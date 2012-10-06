//
//  MBankViewController.m
//  Watch My Money
//
//  Created by Pawel Niewiadomski on 25.08.2012.
//  Copyright (c) 2012 Pawel Niewiadomski. All rights reserved.
//

#import <Foundation/NSJSONSerialization.h>

#import "MBankViewController.h"
#import "Source/mBank/MBankCredentialsWindowController.h"

@interface MBankViewController () {
    NSURL *loginPageUrl;
    NSURL *invalidLoginPageUrl;
    NSURL *framesPageUrl;
    NSURL *accountsListPageUrl;
    NSURL *historyPageUrl;
}

@end

@implementation MBankViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        self.loginForm = [[MBankCredentialsWindowController alloc] init];
        loginPageUrl = [NSURL URLWithString:@"https://www.mbank.com.pl/"];
        invalidLoginPageUrl = [NSURL URLWithString:@"https://www.mbank.com.pl/logon.aspx"];
        framesPageUrl = [NSURL URLWithString:@"https://www.mbank.com.pl/frames.aspx"];
        accountsListPageUrl = [NSURL URLWithString:@"https://www.mbank.com.pl/accounts_list.aspx"];
        historyPageUrl = [NSURL URLWithString:@"https://www.mbank.com.pl/account_oper_list.aspx"];
    }
    
    return self;
}

- (void) loadView {
    [super loadView];
    
    [[NSUserDefaults standardUserDefaults] setBool:TRUE forKey:@"WebKitDeveloperExtras"];
    [[NSUserDefaults standardUserDefaults] synchronize];
    
    NSURLRequest * request = [NSURLRequest requestWithURL:loginPageUrl];
    [[_browser mainFrame] loadRequest:request];
    [_browser setFrameLoadDelegate:self];
}

- (void) webView:(WebView *)sender didFinishLoadForFrame:(WebFrame *)frame {
    NSURL *currentUrl = [[[frame dataSource] request] URL];
    WebScriptObject *windowObject = [frame windowObject];
    
    if([currentUrl isEqual:loginPageUrl]) {
        [self promptForLoginCredentials];
    } else if ([currentUrl isEqual:invalidLoginPageUrl]) {
        [[sender mainFrame] loadRequest:[NSURLRequest requestWithURL:loginPageUrl]];
    } else if ([currentUrl isEqual:accountsListPageUrl]) {
        [self runJavaScript:@"jquery-1.8.0" inDirectory:@"JavaScript" andContext:windowObject];
        
        NSString *accountsJson = [self runJavaScript:@"getAccounts" inDirectory:@"JavaScript/mBank" andContext:windowObject];
        NSError *errors = nil;
        NSArray *accounts = [NSJSONSerialization JSONObjectWithData:[accountsJson dataUsingEncoding:NSUTF8StringEncoding] options:NSJSONReadingMutableLeaves error:&errors];
        [self runJavaScript:@"openHistory" inDirectory:@"JavaScript/mBank" andContext:windowObject];
        [windowObject callWebScriptMethod:@"openHistory" withArguments:@[accounts[0]]];
    } else if ([currentUrl isEqual:historyPageUrl]) {
        [self runJavaScript:@"jquery-1.8.0" inDirectory:@"JavaScript" andContext:windowObject];
        
        id noData = [windowObject evaluateWebScript:@"$('#account_operations_NoData').length"];
        id detailsData = [windowObject evaluateWebScript:@"$('#account_operations').length"];
        if (noData != nil && [noData isKindOfClass:[NSNumber class]] && ((NSNumber *) noData) == [NSNumber numberWithInt:1]) {
            // break, no results
            
        } else if (detailsData != nil && [detailsData isKindOfClass:[NSNumber class]] && [((NSNumber *) detailsData) intValue] > 0) {
            id transactions = [self runJavaScript:@"getTransactions" inDirectory:@"JavaScript/mBank" andContext:windowObject];
            NSLog(@"%@", transactions);
        } else {
            [self runJavaScript:@"getHistory" inDirectory:@"JavaScript/mBank" andContext:windowObject];
            [windowObject callWebScriptMethod:@"getHistory" withArguments:@[@"30"]];
        }
    }
}

- (void) promptForLoginCredentials {
    [NSApp beginSheet:[_loginForm window] modalForWindow:[NSApp keyWindow] modalDelegate:self didEndSelector:@selector(doneEnteringLoginCredentials:returnCode:contextInfo:) contextInfo:nil];
}

- (void) doneEnteringLoginCredentials:(NSWindow *)sheet returnCode:(NSInteger)returnCode contextInfo:(void *)contextInfo {
    [sheet orderOut:self];
    
    if (returnCode == NSOKButton) {
        [self fillLoginFormWithUserId: [_loginForm.userIdField stringValue] andPassword:[_loginForm.passwordField stringValue]];
    }
}

- (id) runJavaScript:(NSString *)scriptName inDirectory:(NSString *) directory andContext:(WebScriptObject *)webScript {
    NSString *filePath = [[NSBundle mainBundle] pathForResource:scriptName ofType:@"js" inDirectory:directory];
    
    NSData *fileData = [NSData dataWithContentsOfFile:filePath];
    
    NSString *jsString = [[NSMutableString alloc] initWithData:fileData encoding:NSUTF8StringEncoding];
    
    return [webScript evaluateWebScript:jsString];
}

- (void) fillLoginFormWithUserId:(NSString *) userId andPassword: (NSString *) password {
    [self runJavaScript:@"jquery-1.8.0" inDirectory:@"JavaScript" andContext:[_browser windowScriptObject]];
    
    [[_browser windowScriptObject] evaluateWebScript:[NSString stringWithFormat: @"$('input[name=customer]').val('%@');",
                                                     userId]];
    [[_browser windowScriptObject] evaluateWebScript:[NSString stringWithFormat: @"$('input[name=password]').val('%@');",
                                                     password]];
    [[_browser windowScriptObject] evaluateWebScript:@"$('#confirm').click();"];
}

@end

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
}

@end

@implementation MBankViewController

@synthesize browser;
@synthesize loginForm;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        loginForm = [[MBankCredentialsWindowController alloc] init];
        loginPageUrl = [NSURL URLWithString:@"https://www.mbank.com.pl/"];
        invalidLoginPageUrl = [NSURL URLWithString:@"https://www.mbank.com.pl/logon.aspx"];
        framesPageUrl = [NSURL URLWithString:@"https://www.mbank.com.pl/frames.aspx"];
    }
    
    return self;
}

- (void) loadView {
    [super loadView];
    
    [[NSUserDefaults standardUserDefaults] setBool:TRUE forKey:@"WebKitDeveloperExtras"];
    [[NSUserDefaults standardUserDefaults] synchronize];
    
    NSURLRequest * request = [NSURLRequest requestWithURL:loginPageUrl];
    [[browser mainFrame] loadRequest:request];
    [browser setFrameLoadDelegate:self];
}

- (void) webView:(WebView *)sender didFinishLoadForFrame:(WebFrame *)frame {
    [self runJavaScript:@"jquery-1.8.0" inDirectory:@"JavaScript" andContext:[frame windowObject]];
    
    NSURL *currentUrl = [[[frame dataSource] request] URL];
    
    if([currentUrl isEqual:loginPageUrl]) {
        [self promptForLoginCredentials];
    } else if ([currentUrl isEqual:invalidLoginPageUrl]) {
        [[sender mainFrame] loadRequest:[NSURLRequest requestWithURL:loginPageUrl]];
    } else if ([currentUrl isEqual:framesPageUrl]) {
        NSString *accountsJson = [self runJavaScript:@"getAccounts" inDirectory:@"JavaScript/mBank" andContext:[[frame findFrameNamed:@"MainFrame" ] windowObject]];
        NSError *errors = nil;
        NSArray *accounts = [NSJSONSerialization JSONObjectWithData:[accountsJson dataUsingEncoding:NSUTF8StringEncoding] options:NSJSONReadingMutableLeaves error:&errors];
        
    }
}

- (void) promptForLoginCredentials {
    [NSApp beginSheet:[loginForm window] modalForWindow:[NSApp keyWindow] modalDelegate:self didEndSelector:@selector(doneEnteringLoginCredentials:returnCode:contextInfo:) contextInfo:nil];
}

- (void) doneEnteringLoginCredentials:(NSWindow *)sheet returnCode:(NSInteger)returnCode contextInfo:(void *)contextInfo {
    [sheet orderOut:self];
    
    if (returnCode == NSOKButton) {
        [self fillLoginFormWithUserId: [loginForm.userIdField stringValue] andPassword:[loginForm.passwordField stringValue]];
    }
}

- (id) runJavaScript:(NSString *)scriptName inDirectory:(NSString *) directory andContext:(WebScriptObject *)webScript {
    NSString *filePath = [[NSBundle mainBundle] pathForResource:scriptName ofType:@"js" inDirectory:directory];
    
    NSData *fileData = [NSData dataWithContentsOfFile:filePath];
    
    NSString *jsString = [[NSMutableString alloc] initWithData:fileData encoding:NSUTF8StringEncoding];
    
    return [webScript evaluateWebScript:jsString];
}

- (void) fillLoginFormWithUserId:(NSString *) userId andPassword: (NSString *) password {
    [[browser windowScriptObject] evaluateWebScript:[NSString stringWithFormat: @"$('input[name=customer]').val('%@');",
                                                     userId]];
    [[browser windowScriptObject] evaluateWebScript:[NSString stringWithFormat: @"$('input[name=password]').val('%@');",
                                                     password]];
    [[browser windowScriptObject] evaluateWebScript:@"$('#confirm').click();"];
}

@end

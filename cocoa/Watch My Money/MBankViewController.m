//
//  MBankViewController.m
//  Watch My Money
//
//  Created by Pawel Niewiadomski on 25.08.2012.
//  Copyright (c) 2012 Pawel Niewiadomski. All rights reserved.
//

#import "MBankViewController.h"
#import "Source/mBank/MBankCredentialsWindowController.h"

@interface MBankViewController ()

@end

@implementation MBankViewController

@synthesize browser;
@synthesize loginForm;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        loginForm = [[MBankCredentialsWindowController alloc] init];
    }
    
    return self;
}

- (void) loadView {
    [super loadView];
    
    NSURLRequest * request = [NSURLRequest requestWithURL:[NSURL URLWithString:@"https://www.mbank.com.pl"]];
    [[browser mainFrame] loadRequest:request];
    [browser setFrameLoadDelegate:self];
}

- (void) webView:(WebView *)sender didFinishLoadForFrame:(WebFrame *)frame {
    [self attachJQuery:sender];
    
    [self promptForLoginCredentials];
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

- (void) attachJQuery:(WebView *) webView {
    NSString *filePath = [[NSBundle mainBundle] pathForResource:@"jquery-1.8.0" ofType:@"js" inDirectory:@"JavaScript"];
                          
    NSData *fileData = [NSData dataWithContentsOfFile:filePath];
                          
    NSString *jsString = [[NSMutableString alloc] initWithData:fileData encoding:NSUTF8StringEncoding];
                          
    [[webView windowScriptObject ] evaluateWebScript:jsString];
}

- (void) fillLoginFormWithUserId:(NSString *) userId andPassword: (NSString *) password {
    [[browser windowScriptObject] evaluateWebScript:[NSString stringWithFormat: @"$('input[name=customer]').val('%@');",
                                                     userId]];
    [[browser windowScriptObject] evaluateWebScript:[NSString stringWithFormat: @"$('input[name=password]').val('%@');",
                                                     password]];
    [[browser windowScriptObject] evaluateWebScript:@"$('#confirm').click();"];
}

@end

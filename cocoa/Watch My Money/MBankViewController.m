//
//  MBankViewController.m
//  Watch My Money
//
//  Created by Pawel Niewiadomski on 25.08.2012.
//  Copyright (c) 2012 Pawel Niewiadomski. All rights reserved.
//

#import "MBankViewController.h"

@interface MBankViewController ()

@end

@implementation MBankViewController

@synthesize browser;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // do initialization here
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
    [NSApp beginSheet:loginForm modalForWindow:[NSApp keyWindow] modalDelegate:self didEndSelector:@selector(doneEnteringLoginCredentials:returnCode:contextInfo:) contextInfo:nil];
}

- (IBAction) closeLoginSheet: (id)sender {
    [NSApp endSheet:loginForm];
}

- (void) doneEnteringLoginCredentials:(NSWindow *)sheet returnCode:(NSInteger)returnCode contextInfo:(void *)contextInfo {
    [sheet orderOut:self];
}

- (void) attachJQuery:(WebView *) webView {
    NSString *filePath = [[NSBundle mainBundle] pathForResource:@"jquery-1.8.0" ofType:@"js" inDirectory:@"JavaScript"];
                          
    NSData *fileData = [NSData dataWithContentsOfFile:filePath];
                          
    NSString *jsString = [[NSMutableString alloc] initWithData:fileData encoding:NSUTF8StringEncoding];
                          
    [[webView windowScriptObject ] evaluateWebScript:jsString];
}

- (void) fillLoginFormWithUserId:(NSString *) userId andPassword: (NSString *) password {
    [[browser windowScriptObject] evaluateWebScript:@"$('input[name=customer]').val('4359348579');"];
    [[browser windowScriptObject] evaluateWebScript:@"$('input[name=password]').val('4359348579');"];
    [[browser windowScriptObject] evaluateWebScript:@"$('#confirm').submit();"];
}

@end

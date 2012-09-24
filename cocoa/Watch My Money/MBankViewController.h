//
//  MBankViewController.h
//  Watch My Money
//
//  Created by Pawel Niewiadomski on 25.08.2012.
//  Copyright (c) 2012 Pawel Niewiadomski. All rights reserved.
//

#import <Cocoa/Cocoa.h>
#import <WebKit/WebKit.h>

@class MBankCredentialsWindowController;

@interface MBankViewController : NSViewController {
}

@property (nonatomic, weak) IBOutlet WebView *browser;
@property (nonatomic, retain) MBankCredentialsWindowController *loginForm;

- (void) loadView;
- (void) webView:(WebView *)sender didFinishLoadForFrame:(WebFrame *)frame;
- (void) fillLoginFormWithUserId:(NSString *) userId andPassword: (NSString *) password;
- (void) promptForLoginCredentials;
- (void) doneEnteringLoginCredentials:(NSWindow *)sheet returnCode:(NSInteger)returnCode contextInfo:(void *)contextInfo;
- (id) runJavaScript:(NSString *) scriptName inDirectory:(NSString *) directory andContext: (WebScriptObject *) webScript;

@end

//
//  MBankViewController.h
//  Watch My Money
//
//  Created by Pawel Niewiadomski on 25.08.2012.
//  Copyright (c) 2012 Pawel Niewiadomski. All rights reserved.
//

#import <Cocoa/Cocoa.h>
#import <WebKit/WebKit.h>

@interface MBankViewController : NSViewController {
    IBOutlet WebView *browser;
    IBOutlet NSWindow *loginForm;
}

@property (nonatomic, retain) WebView *browser;

- (void) loadView;
- (void) webView:(WebView *)sender didFinishLoadForFrame:(WebFrame *)frame;
- (void) attachJQuery:(WebView *) webView;
- (void) fillLoginFormWithUserId:(NSString *) userId andPassword: (NSString *) password;
- (void) promptForLoginCredentials;
- (void) doneEnteringLoginCredentials:(NSWindow *)sheet returnCode:(NSInteger)returnCode contextInfo:(void *)contextInfo;
- (IBAction) closeLoginSheet: (id)sender;

@end

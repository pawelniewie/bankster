//
//  MBankCredentialsViewController.h
//  Watch My Money
//
//  Created by Pawel Niewiadomski on 30.08.2012.
//  Copyright (c) 2012 Pawel Niewiadomski. All rights reserved.
//

#import <Cocoa/Cocoa.h>

@interface MBankCredentialsWindowController : NSWindowController {
    IBOutlet NSTextField *userIdField;
    IBOutlet NSSecureTextField *passwordField;
    IBOutlet NSButton *cancelButton;
    IBOutlet NSButton *logInButton;
}

@property (nonatomic, retain) NSTextField *userIdField;
@property (nonatomic, retain) NSSecureTextField *passwordField;
@property (nonatomic, retain) NSButton *cancelButton;
@property (nonatomic, retain) NSButton *logInButton;

- (id) init;
- (IBAction) closeLoginSheet: (id)sender;

@end

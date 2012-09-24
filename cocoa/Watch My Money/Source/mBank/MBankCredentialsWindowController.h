//
//  MBankCredentialsViewController.h
//  Watch My Money
//
//  Created by Pawel Niewiadomski on 30.08.2012.
//  Copyright (c) 2012 Pawel Niewiadomski. All rights reserved.
//

#import <Cocoa/Cocoa.h>

@interface MBankCredentialsWindowController : NSWindowController {
}

@property (nonatomic, weak) IBOutlet NSTextField *userIdField;
@property (nonatomic, weak) IBOutlet NSSecureTextField *passwordField;
@property (nonatomic, weak) IBOutlet NSButton *cancelButton;
@property (nonatomic, weak) IBOutlet NSButton *logInButton;

- (id) init;
- (IBAction) closeLoginSheet: (id)sender;

@end

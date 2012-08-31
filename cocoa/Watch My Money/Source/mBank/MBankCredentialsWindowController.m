//
//  MBankCredentialsViewController.m
//  Watch My Money
//
//  Created by Pawel Niewiadomski on 30.08.2012.
//  Copyright (c) 2012 Pawel Niewiadomski. All rights reserved.
//

#import "MBankCredentialsWindowController.h"

@interface MBankCredentialsWindowController ()

@end

@implementation MBankCredentialsWindowController

- (id)init
{
    self = [super initWithWindowNibName:@"MBankCredentialsWindowController"];
    if (self) {
        // Initialization code here.
    }
    
    return self;
}

- (IBAction) closeLoginSheet: (id)sender {
    [NSApp endSheet:self.window returnCode: (sender == self.logInButton ? NSOKButton : NSCancelButton)];
}

@end

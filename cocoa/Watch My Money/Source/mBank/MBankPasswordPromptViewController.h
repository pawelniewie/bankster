//
//  MBankPasswordPromptViewController.h
//  Watch My Money
//
//  Created by Pawel Niewiadomski on 26.08.2012.
//  Copyright (c) 2012 Pawel Niewiadomski. All rights reserved.
//

#import <Cocoa/Cocoa.h>

@interface MBankPasswordPromptViewController : NSViewController {
    IBOutlet NSTextField *userId;
    IBOutlet NSTextField *password;
    IBOutlet NSButton *cancel;
    IBOutlet NSButton *submit;
}

@end

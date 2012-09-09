//
//  SelectBankViewController.m
//  Watch My Money
//
//  Created by Pawel Niewiadomski on 24.08.2012.
//  Copyright (c) 2012 Pawel Niewiadomski. All rights reserved.
//

#import "SelectBankViewController.h"

@interface SelectBankViewController ()

@end

@implementation SelectBankViewController

@synthesize banks;
@synthesize bankNames;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        bankNames = @[@"mBank", @"Eurobank"];
    }
    return self;
}

- (int) numberOfRowsInTableView:(NSTableView *)aTableView {
    return (int) [bankNames count];
}

- (id) tableView:(NSTableView *)aTableView objectValueForTableColumn:(NSTableColumn *)aTableColumn row:(int)rowIndex {
    return bankNames[rowIndex];
}

@end

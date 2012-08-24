//
//  SelectBankViewController.h
//  Watch My Money
//
//  Created by Pawel Niewiadomski on 24.08.2012.
//  Copyright (c) 2012 Pawel Niewiadomski. All rights reserved.
//

#import <Cocoa/Cocoa.h>

@interface SelectBankViewController : NSViewController <NSTableViewDataSource> {
    IBOutlet NSTableView *banks;
}

@property (readonly, nonatomic) NSTableView *banks;
@property (readonly, nonatomic) NSArray *bankNames;

- (int) numberOfRowsInTableView:(NSTableView *)aTableView;
- (id) tableView:(NSTableView *)aTableView objectValueForTableColumn:(NSTableColumn *)aTableColumn row:(int)rowIndex;

@end



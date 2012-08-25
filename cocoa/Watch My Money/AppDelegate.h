//
//  AppDelegate.h
//  Watch My Money
//
//  Created by Pawel Niewiadomski on 10.08.2012.
//  Copyright (c) 2012 Pawel Niewiadomski. All rights reserved.
//

#import <Cocoa/Cocoa.h>
#import "SelectBankViewController.h"
#import "MBankViewController.h"

@interface AppDelegate : NSObject <NSApplicationDelegate>

@property (assign) IBOutlet NSWindow *window;
@property (readonly, nonatomic) SelectBankViewController *banksView;
@property (readonly, nonatomic) MBankViewController *mBankView;

@property (readonly, strong, nonatomic) NSPersistentStoreCoordinator *persistentStoreCoordinator;
@property (readonly, strong, nonatomic) NSManagedObjectModel *managedObjectModel;
@property (readonly, strong, nonatomic) NSManagedObjectContext *managedObjectContext;

- (IBAction)saveAction:(id)sender;

@end

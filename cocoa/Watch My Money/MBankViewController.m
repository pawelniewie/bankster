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
    
    [[browser mainFrame] loadRequest:[NSURLRequest requestWithURL:[NSURL URLWithString:@"https://www.mbank.com.pl"]]];
}

@end

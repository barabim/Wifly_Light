//
//  NWEditGradientCommandViewController.h
//  WyLightRemote
//
//  Created by Nils Weiß on 13.08.13.
//  Copyright (c) 2013 Nils Weiß. All rights reserved.
//

#import <UIKit/UIKit.h>

@class NWSetGradientScriptCommandObject;

@interface NWEditGradientCommandViewController : UIViewController

@property (nonatomic, strong) NWSetGradientScriptCommandObject *command;

- (IBAction)unwindChangeColorDone:(UIStoryboardSegue *)segue;

- (IBAction)unwindChangeColorCancel:(UIStoryboardSegue *)segue;


@end
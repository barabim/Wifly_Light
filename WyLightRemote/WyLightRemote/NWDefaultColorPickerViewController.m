//
//  NWDefaultColorPickerViewController.m
//  WyLightRemote
//
//  Created by Nils Weiß on 15.08.13.
//  Copyright (c) 2013 Nils Weiß. All rights reserved.
//

#import "NWDefaultColorPickerViewController.h"
#import "KZColorPicker.h"

@implementation NWDefaultColorPickerViewController

- (void)loadView {
	UIView *container = [[UIView alloc] initWithFrame:[[UIScreen mainScreen] applicationFrame]];
	
	container.autoresizingMask = UIViewAutoresizingFlexibleHeight | UIViewAutoresizingFlexibleWidth;
	container.backgroundColor = [UIColor clearColor];
	self.view = container;
	
	KZColorPicker *picker = [[KZColorPicker alloc] initWithFrame:container.bounds];
    picker.autoresizingMask = UIViewAutoresizingFlexibleHeight | UIViewAutoresizingFlexibleWidth;
	picker.selectedColor = self.selectedColor;
    picker.oldColor = self.selectedColor;
	[picker addTarget:self action:@selector(pickerChanged:) forControlEvents:UIControlEventValueChanged];
	[self.view addSubview:picker];
}

- (void) pickerChanged:(KZColorPicker *)cp {
    self.selectedColor = cp.selectedColor;
	if (self.colorPickerDelegate) {
		[self.colorPickerDelegate defaultColorController:self didChangeColor:self.selectedColor];
	}
}

- (void) viewWillAppear:(BOOL)animated {
	[super viewWillAppear:animated];
}

#pragma mark - HANDLE ROTATION

- (void)willRotateToInterfaceOrientation:(UIInterfaceOrientation)toInterfaceOrientation duration:(NSTimeInterval)duration {
	[super willRotateToInterfaceOrientation:toInterfaceOrientation duration:duration];
	
	if (toInterfaceOrientation == UIInterfaceOrientationLandscapeLeft || toInterfaceOrientation == UIInterfaceOrientationLandscapeRight) {
		[self.tabBarController.tabBar setHidden:YES];
	}
	else {
		[self.tabBarController.tabBar setHidden:NO];
	}
}

- (void)didRotateFromInterfaceOrientation:(UIInterfaceOrientation)fromInterfaceOrientation {
	[super didRotateFromInterfaceOrientation:fromInterfaceOrientation];
	
	if (UIInterfaceOrientationIsLandscape([[UIApplication sharedApplication] statusBarOrientation]))
	{
		CGRect biggerFrame = self.tabBarController.view.frame;
		biggerFrame.size.height += self.tabBarController.tabBar.frame.size.height;
		self.tabBarController.view.frame = biggerFrame ;
	}
}

@end
var api = function() {}
api.bind = function(key, combo, action) {}
api.alert = function(message) {}

var App = function() {}
App.runningApps = function() {}
App.allWindows = function() {}
App.visibleWindows = function() {}
App.title = function() {}
App.bundleIdentifier = function() {}
App.isHidden = function() {}
App.show = function() {}
App.hide = function() {}
App.activate = function() {}
App.pid = function() {}
App.kill = function() {}
App.kill9 = function() {}

var Window = function() {}
// static
Window.allWindows = function() {}
Window.visibleWindows = function() {}
Window.focusedWindow = function() {}
Window.visibleWindowsMostRecentFirst = function() {}
Window.otherWindowsOnSameScreen = function() {}
Window.otherWindowsOnAllScreens = function() {}

// Members
Window.frame = function() {}
Window.topLeft = function() {}
Window.size = function() {}
// Rect frame
Window.setFrame = function(frame) {}
// Point point
Window.setTopLeft = function(point) {}
// Size size
Window.setSize = function(size) {}
Window.maximize = function() {}
Window.minimize = function() {}
Window.unMinimize = function() {}
Window.screen = function() {}
Window.app = function() {}
Window.isNormalWindow = function() {}
Window.focusWindow = function() {}
Window.focusWindowLeft = function() {}
Window.focusWindowRight = function() {}
Window.focusWindowUp = function() {}
Window.focusWindowDown = function() {}
Window.windowsToWest = function() {}
Window.windowsToEast = function() {}
Window.windowsToNorth = function() {}
Window.windowsToSouth = function() {}
Window.title = function() {}
Window.isWindowMinimized = function() {}


var Screen = function() {}
Screen.frameIncludingDockAndMenu = function() {}
Screen.frameWithoutDockOrMenu = function() {}
Screen.nextScreen = function() {}
Screen.previousScreen = function() {}


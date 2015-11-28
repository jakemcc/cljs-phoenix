var Phoenix = function() {}
// static
Phoenix.reload = function() {}
Phoenix.bind = function(key, modifiers, callback) {}
Phoenix.on = function(event, callback) {}
Phoenix.log = function(message) {}
Phoenix.notify = function(message) {}

var App = function() {}
//static
App.launch = function(appName) {}
App.focusedApp = function() {}
App.runningApps = function() {}

App.processIdentifier = function() {}
App.bundleIdentifier = function() {}
App.name = function() {}
App.isActive = function() {}
App.isHidden = function() {}
App.isTerminated = function() {}
App.mainWindow = function() {}
App.windows = function() {}
App.visibleWindows = function() {}
App.activate = function() {}
App.focus = function() {}
App.show = function() {}
App.hide = function() {}
App.terminate = function() {}
App.forceTerminate = function() {}

var Window = function() {}
// static
Window.windows = function() {}
Window.visibleWindows = function() {}
Window.focusedWindow = function() {}
Window.visibleWindowsInOrder = function() {}
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
Window.unminimize = function() {}
Window.screen = function() {}
Window.app = function() {}
Window.isNormal = function() {}
Window.focus = function() {}
Window.focusClosestWindowInLeft = function() {}
Window.focusClosestWindowInRight = function() {}
Window.focusClosestWindowInUp = function() {}
Window.focusClosestWindowInDown = function() {}
Window.windowsToWest = function() {}
Window.windowsToEast = function() {}
Window.windowsToNorth = function() {}
Window.windowsToSouth = function() {}
Window.title = function() {}
Window.isMinimized = function() {}


var Screen = function() {}
Screen.frameInRectangle = function() {}
Screen.visibleFrameInRectangle = function() {}
Screen.mainScreen = function() {}
Screen.next = function() {}
Screen.previous = function() {}
Screen.screens = function() {}
Screen.windows = function() {}
Screen.visibleWindows = function() {}

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
App.focused = function() {}
App.all = function() {}

App.processIdentifier = function() {}
App.bundleIdentifier = function() {}
App.name = function() {}
App.isActive = function() {}
App.isHidden = function() {}
App.isTerminated = function() {}
App.mainWindow = function() {}
App.windows = function() {}
App.activate = function() {}
App.focus = function() {}
App.show = function() {}
App.hide = function() {}
App.terminate = function() {}

var Window = function() {}
// static
Window.all = function() {}
Window.focused = function() {}
Window.recent = function() {}
Window.others = function() {}

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
Window.focusClosestNeighbor = function() {}
Window.neighbors = function() {}
Window.title = function() {}
Window.isMinimized = function() {}


var Screen = function() {}
Screen.frameInRectangle = function() {}
Screen.flippedVisibleFrame = function() {}
Screen.main = function() {}
Screen.next = function() {}
Screen.previous = function() {}
Screen.all = function() {}
Screen.windows = function() {}

var Model = function() {}
// properties
Model.origin = function() {}
Model.duration = function() {}
Model.message = function() {}

Model.frame = function() {}
Model.show = function() {}
Model.close = function() {}

var Key = function() {}

# AndroidMockApi
This is a demo project show how to add the **"mock server api"** feature to an android project which use gradle.

The code is very simple, You can go through it in few minutes.
If your project has the same requirements, try it out!

# How it works
The architecture contains "**UI Layer**" and "**Data Layer**", the two layers communicate by DataApi interfaces. Then we can provide "Real Data Access Object" or "Mock Data Access Object" with no modification to UI code.

For example, `ITaskApi` defines a method `void getTasks(DataApiCallback<List<Task>> callback)` to get all Task objects.
The UI code use `ITaskApi.getTasks()` to fetch data， and the real instance is provide by `DataApiManager.ofTask()` —— which can give mock implement for test needs.

`DataApiManager` use `MockApiManager.getMockApi()` to get a mock instance.

Here we use the ability of gradle to privide different `MockApiManager` .java file for debug and release build.

Debug build would provide Mock, while Release build will not, and have no extra code to be compiled to the final apk.

`IMockApiStrategy` defines how to mock the response behavior like a real server does, but you can control how it works to fit your test needs.

`BaseMockApi` implements most logic to give `error or success response` for the DataApi requests. It use RxJava to manage Thread issues, and give some time delay like the real network operations.

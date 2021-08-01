# delivery
Delivery app is an application, which makes use of Hilt, MVVM, Paging3, Room, Retrofit and Navigation to simuate the scenario that users review their delivery items.

For this app, I have created stub and nonstub version, which is caused by instablility of backend api. 
If we select stubDebug as the build variant, you will get all the mock data from stub/assets, while the nonstubDebug will provide real data from backend.

For this project, I have created two branches, namely master and test. Test branch is the latest branch with instrumented test for DB dao, RemoteMediator and Fragments.

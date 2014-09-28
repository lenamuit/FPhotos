# FPhotos - My photos on facebook #
=======
Project is an application which use Facebook SDK to get albums and photos of users. 
They can view their photos tagged in, their photos in albums etc...

First version had released. https://play.google.com/store/apps/details?id=com.namlh.fphotos

**My purpose is research about Dependency Injector when develop an Android project, define how to develop a project with many modules, flexible to change or update modules. 
Support unit test and drive test model**

### Continuous Integration result ###

[![Build Status](https://travis-ci.org/lenamuit/FPhotos.svg?branch=master)](https://travis-ci.org/lenamuit/FPhotos)

### Project using dependency injector framework ([Dagger](http://square.github.io/dagger/)) with following modules


* [Api Module](https://github.com/lenamuit/FPhotos/blob/master/app/src/main/java/vn/lenam/imagegallery/api/ApiModule.java)

  Provide the classes to get data from Facebook

* [Data provider module](https://github.com/lenamuit/FPhotos/blob/master/app/src/main/java/vn/lenam/imagegallery/data/DataModule.java)
  
  Provide the classes to store data in local memory such as caches, preference etc...

* [Google Drive module](https://github.com/lenamuit/FPhotos/blob/master/app/src/main/java/vn/lenam/imagegallery/services/drive/DriveModule.java)
 
  Provice the classes to connect and upload photos into Google Drive

* [Dropbox module](https://github.com/lenamuit/FPhotos/blob/master/app/src/main/java/vn/lenam/imagegallery/services/dropbox/DropboxModule.java)

  Provide the classes to connect and upload photos into Dropbox  

* [Main Module](https://github.com/lenamuit/FPhotos/blob/master/app/src/main/java/vn/lenam/imagegallery/ui/main/MainModule.java)

  Provide Model class, Presenter class and Viewer class for applicaion. 


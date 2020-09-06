[![Platform](https://img.shields.io/badge/platform-android-yellow.svg)]()
[![Programming Language](https://img.shields.io/badge/language-kotlin-orange.svg)]()

![Bridge International Academies Logo](BannerLogo280x60.png)

# Android Technical Test


## Table of content

- [App Requirements](#app-requirements)
- [Requirements Covered](#requirements-covered)
- [Dependencies](#dependencies)
- [Usage](#usage)
- [Contributor](#contributor)

## App Requirements

The requirements from the business owner are:

1. I need to be able to see a list of all pupils.
2. I need to be able to add a new pupil and submit.
3. The above requirements should continue when I am offline.  With data synchronising when I'm next online.

In addition, you should also:

1. Write a short ReadMe about your code, your design, assumptions made and which requirements are implemented.
2. Write production quality code.
3. Submit your source code as a .zip file. Also, do not include any binary files in your final solution.

## Requirements Covered

- Android 6+
- MVVM (Model-View-ViewModel)
- Ability to view list of pupils (Each request will render 5 pupils).
- Ability to delete single pupil data.
- ViewModel, Repository, DI, Network & Database layer has been created
  for AddPupil Screen and PupilDetails Screen, But Only UI is pending
  due to time limitation.
- Some tests are covered with MockWebServer, 100% code coverage wasn't
  possible due to time limitation, But can be covered in next sprint.
- Portrait and landscape orientation support.
- Dependency injection for ViewModels, Adapter and Repository.

## Dependencies

Project internally uses following dependencies along with Android
Jetpack components:

- [Dagger2](https://github.com/google/dagger)
- [Retrofit](https://github.com/square/retrofit)
- [Gson](https://github.com/google/gson)
- [JUnit](https://github.com/junit-team/junit4)
- [Glide](https://github.com/bumptech/glide)

## Usage

For a working implementation of this project see the app/ folder.

## Contributor

* Onkar Nene - omkarn.92@gmail.com

# Change Log - @typespec/protobuf

## 0.71.0

No changes, version bump only.

## 0.70.0

### Features

- [#7199](https://github.com/microsoft/typespec/pull/7199) Add "capitalize" string helper to compiler


## 0.69.0

### Bug Fixes

- [#7069](https://github.com/microsoft/typespec/pull/7069) Handle types without node


## 0.68.0

### Bump dependencies

- [#6595](https://github.com/microsoft/typespec/pull/6595) Upgrade dependencies


## 0.67.0

### Features

- [#6327](https://github.com/microsoft/typespec/pull/6327) Remove reference to delete projection feature

### Bump dependencies

- [#6266](https://github.com/microsoft/typespec/pull/6266) Update dependencies

### Bug Fixes

- [#6411](https://github.com/microsoft/typespec/pull/6411) Add support for new `dryRun` emitter option


## 0.66.0

No changes, version bump only.

## 0.65.0

### Bump dependencies

- [#5690](https://github.com/microsoft/typespec/pull/5690) Upgrade dependencies


## 0.64.0

No changes, version bump only.

## 0.63.0

No changes, version bump only.

## 0.62.0

### Bump dependencies

- [#4679](https://github.com/microsoft/typespec/pull/4679) Upgrade dependencies - October 2024


## 0.61.0

### Bump dependencies

- [#4424](https://github.com/microsoft/typespec/pull/4424) Bump dependencies


## 0.60.0

### Features

- [#4139](https://github.com/microsoft/typespec/pull/4139) Internals: Migrate to new api for declaring decorator implementation


## 0.59.0

### Bug Fixes

- [#3933](https://github.com/microsoft/typespec/pull/3933) Fix some diagnostic not showing the right message

### Bump dependencies

- [#3948](https://github.com/microsoft/typespec/pull/3948) Update dependencies


## 0.58.0

### Bump dependencies

- [#3718](https://github.com/microsoft/typespec/pull/3718) Dependency updates July 2024


## 0.57.0

### Bug Fixes

- [#3022](https://github.com/microsoft/typespec/pull/3022) Update to support new value types
- [#3561](https://github.com/microsoft/typespec/pull/3561) Corrected cross-package reference behavior in some buggy cases.

### Bump dependencies

- [#3401](https://github.com/microsoft/typespec/pull/3401) Update dependencies - May 2024


## 0.56.0

### Bump dependencies

- [#3169](https://github.com/microsoft/typespec/pull/3169) Update dependencies


## 0.55.0

### Bump dependencies

- [#3027](https://github.com/microsoft/typespec/pull/3027) Update dependencies


## 0.54.0

### Bump dependencies

- [#2900](https://github.com/microsoft/typespec/pull/2900) Update dependencies


## 0.53.0

### Minor Changes

- a3d6acf: Added support for template name expansion to the protobuf emitter.

### Patch Changes



## 0.52.0

Wed, 24 Jan 2024 05:46:53 GMT

### Updates

- Rename template parameters in preparation for named template argument instantiation.
- Update dependencies

## 0.51.0

Wed, 06 Dec 2023 19:40:58 GMT

_Version update only_

## 0.50.0

Wed, 08 Nov 2023 00:07:17 GMT

_Version update only_

## 0.49.0

Wed, 11 Oct 2023 23:31:35 GMT

### Updates

- Added support for emitting documentation comments in protobuf specifications.
- Update dependencies

## 0.48.0

Tue, 12 Sep 2023 21:47:11 GMT

### Updates

- Added support for the 'omit-unreachable-types' option.
- Automatically convert empty operation parameters into a reference to 'google.protobuf.Empty' instead of synthesizing an empty model.

## 0.47.0

Tue, 08 Aug 2023 22:32:10 GMT

_Version update only_

## 0.46.0

Tue, 11 Jul 2023 22:06:00 GMT

### Updates

- Update dependencies

## 0.44.0

Tue, 06 Jun 2023 22:44:16 GMT

### Minor changes

- Uptake doc comment changes
- Update decorators to use `valueof`

### Patches

- Update decorators signature to use `{}` instead of object

### Updates

- Fixed a test harness issue requiring unnecessary re-recording of protobuf tests.

## 0.43.1

Wed, 10 May 2023 21:24:00 GMT

### Patches

- Update compiler to be a peer dependency

all: android ios web

# The gradle plugin will take care of building the bindings too
android: setup
	cd platforms/android && \
		./gradlew publishToMavenLocal

android-bindings: android-bindings-armv7 android-bindings-aarch64 android-bindings-x86_64

android-bindings-armv7:
	cd bindings/ex-feature-ffi && \
		cargo build --release --target armv7-linux-androideabi

android-bindings-aarch64:
	cd bindings/ex-feature-ffi && \
		cargo build --release --target aarch64-linux-android

android-bindings-x86_64:
	cd bindings/ex-feature-ffi && \
		cargo build --release --target x86_64-linux-android
	# Not copying into the Android project here, since the gradle plugin
	# actually performs this build itself.


IOS_PACKAGE_DIR := ../../platforms/ios/lib/ExFeature
IOS_GENERATION_DIR := .generated/ios

ios: setup
	cd bindings/ex-feature-ffi && \
	cargo build --release --target aarch64-apple-ios && \
	cargo build --release --target aarch64-apple-ios-sim && \
	cargo build --release --target x86_64-apple-ios && \
	mkdir -p ../../target/ios-simulator && \
	lipo -create \
	  ../../target/x86_64-apple-ios/release/libuniffi_ex_feature.a \
	  ../../target/aarch64-apple-ios-sim/release/libuniffi_ex_feature.a \
	  -output ../../target/ios-simulator/libuniffi_ex_feature.a && \
	rm -rf ${IOS_PACKAGE_DIR}/ExFeatureFFI.xcframework && \
	rm -f ${IOS_PACKAGE_DIR}/Sources/ExFeature/ExFeature.swift && \
	rm -rf ${IOS_GENERATION_DIR} && \
	mkdir -p ${IOS_GENERATION_DIR} && \
	uniffi-bindgen \
		generate src/ex_feature.udl \
		--language swift \
		--config uniffi.toml \
		--out-dir ${IOS_GENERATION_DIR} && \
	mkdir -p ${IOS_GENERATION_DIR}/headers && \
	mv ${IOS_GENERATION_DIR}/*.h         ${IOS_GENERATION_DIR}/headers/ && \
	mv ${IOS_GENERATION_DIR}/*.modulemap ${IOS_GENERATION_DIR}/headers/module.modulemap && \
	mv ${IOS_GENERATION_DIR}/*.swift     ${IOS_PACKAGE_DIR}/Sources/ExFeature/ && \
	xcodebuild -create-xcframework \
	  -library ../../target/aarch64-apple-ios/release/libuniffi_ex_feature.a \
	  -headers ${IOS_GENERATION_DIR}/headers \
	  -library ../../target/ios-simulator/libuniffi_ex_feature.a \
	  -headers ${IOS_GENERATION_DIR}/headers \
	  -output ${IOS_PACKAGE_DIR}/ExFeatureFFI.xcframework && \
# Note: we use sed to tweak the generated Swift bindings and catch Rust panics, 

web: setup
	cd bindings/ex-feature-wasm && \
	npm install && \
	npm run build && \
	mkdir -p ../../platforms/web/generated && \
	cp \
		pkg/ex_feature_bg.wasm \
		pkg/ex_feature_bg.wasm.d.ts \
		pkg/ex_feature.d.ts \
		pkg/ex_feature.js \
		../../platforms/web/generated/
	cd platforms/web && yarn install && yarn build

web-format:
	cd platforms/web && \
	yarn prettier --write .

clean:
	cargo clean
	rm -rf bindings/ex-feature-wasm/node_modules
	rm -rf bindings/ex-feature-wasm/pkg
	rm -rf bindings/ex-feature-ffi/src/generated
	rm -rf platforms/android/out
	cd platforms/android && ./gradlew clean


test:
	cargo test
	cd platforms/web && yarn tsc && yarn test

coverage:
	@echo "Requires `rustup component add llvm-tools-preview`"
	@echo "Requires `cargo install cargo-llvm-cov`"
	cargo llvm-cov --open

setup:
	cargo install uniffi_bindgen --version 0.21.0


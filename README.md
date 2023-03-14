# ElementX feature module sample

## Building the code

Get the prerequisites for each platform by reading the READMEs for them:

* WASM/JavaScript:
  [bindings/ex-feature-wasm/README.md](bindings/ex-feature-wasm/README.md)

* Android/Kotlin or iOS/Swift:
  [bindings/ex-feature-ffi/README.md](bindings/ex-feature-ffi/README.md)

Now, to build all the bindings, try:

```bash
make
```

To build for a single platform, or to learn more, see the individual README
files above.

## Release the code

See [RELEASE.md](RELEASE.md).

## More info

For more detailed explanations and examples of platform-specific code to use
Rust bindings like those generated here, see
[Building cross-platform Rust for Web, Android and iOS – a minimal example](https://www.artificialworlds.net/blog/2022/07/06/building-cross-platform-rust-for-web-android-and-ios-a-minimal-example/).

## License

[Apache-2.0](https://www.apache.org/licenses/LICENSE-2.0)

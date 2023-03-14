fn main() {
    uniffi_build::generate_scaffolding("./src/ex_feature.udl")
        .expect("Building the UDL file failed");
}

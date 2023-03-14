use std::sync::{Arc, Mutex};

#[derive(Default)]
pub struct ExFeature {
    inner: Mutex<ex_feature::ExFeature>,
}

impl ExFeature {
    pub fn new() -> Self {
        Self {
            inner: Mutex::new(ex_feature::ExFeature::new()),
        }
    }

    pub fn name(self: &Arc<Self>) -> String {
        self.inner.lock().unwrap().name().to_owned()
    }
}

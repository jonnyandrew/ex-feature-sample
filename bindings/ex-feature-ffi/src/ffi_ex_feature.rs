use std::sync::{Arc, Mutex};

pub struct ExFeature {
    inner: Mutex<ex_feature::ExFeature>,
}

pub struct SlidingSyncUpdate {
    pub lists: Vec<String>,
    pub rooms: Vec<String>,
}

impl From<SlidingSyncUpdate> for ex_feature::SlidingSyncUpdate {
    fn from(from: SlidingSyncUpdate) -> Self {
        ex_feature::SlidingSyncUpdate {
            lists: from.lists,
            rooms: from.rooms,
        }
    }
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

    pub fn log_tag(self: &Arc<Self>) -> String {
        self.inner.lock().unwrap().log_tag().to_owned()
    }

    pub fn process_sliding_sync_update(
        self: &Arc<Self>,
        ss_update: SlidingSyncUpdate,
    ) -> String {
        self.inner
            .lock()
            .unwrap()
            .process_sliding_sync_update(ss_update.into())
    }
}

impl Default for ExFeature {
    fn default() -> Self {
        Self::new()
    }
}

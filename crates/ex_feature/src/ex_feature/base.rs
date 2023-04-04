// Copyright 2022 The Matrix.org Foundation C.I.C.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

pub struct SlidingSyncUpdate {
    pub lists: Vec<String>,
    pub rooms: Vec<String>,
}

pub struct ExFeature {
    pub name: String,
    pub log_tag: String,
}

impl ExFeature {
    pub fn new() -> Self {
        Self::default()
    }

    pub fn name(&self) -> &str {
        &self.name
    }

    pub fn log_tag(&self) -> &str {
        &self.log_tag
    }

    pub fn process_sliding_sync_update(
        &self,
        ss_update: SlidingSyncUpdate,
    ) -> String {
        let num_lists = ss_update.lists.len();
        let num_rooms = ss_update.rooms.len();

        format!(
            "Sliding sync updated {} lists and {} rooms",
            num_lists, num_rooms,
        )
    }
}

impl Default for ExFeature {
    fn default() -> Self {
        Self {
            name: "Sample feature".into(),
            log_tag: "SAMPLE_FEATURE".into(),
        }
    }
}

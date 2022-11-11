use std::collections::HashMap;
use std::sync::{Arc, Mutex};

use widestring::Utf16String;

use crate::ffi_composer_state::ComposerState;
use crate::ffi_composer_update::ComposerUpdate;
use crate::ffi_menu_state::build_composer_action_states;
use crate::{ActionState, ComposerAction};

pub struct ComposerModel {
    inner: Mutex<wysiwyg::ComposerModel<Utf16String>>,
}

impl ComposerModel {
    pub fn new() -> Self {
        Self {
            inner: Mutex::new(wysiwyg::ComposerModel::new()),
        }
    }

    pub fn set_content_from_html(
        self: &Arc<Self>,
        html: String,
    ) -> Arc<ComposerUpdate> {
        let html = Utf16String::from_str(&html);
        Arc::new(ComposerUpdate::from(
            self.inner.lock().unwrap().set_content_from_html(&html),
        ))
    }

    pub fn set_content_from_markdown(
        self: &Arc<Self>,
        markdown: String,
    ) -> Arc<ComposerUpdate> {
        let markdown = Utf16String::from_str(&markdown);
        Arc::new(ComposerUpdate::from(
            self.inner
                .lock()
                .unwrap()
                .set_content_from_markdown(&markdown),
        ))
    }

    pub fn get_content_as_html(self: &Arc<Self>) -> String {
        self.inner.lock().unwrap().get_content_as_html().to_string()
    }

    pub fn get_content_as_markdown(self: &Arc<Self>) -> String {
        self.inner
            .lock()
            .unwrap()
            .get_content_as_markdown()
            .to_string()
    }

    pub fn clear(self: &Arc<Self>) -> Arc<ComposerUpdate> {
        Arc::new(ComposerUpdate::from(self.inner.lock().unwrap().clear()))
    }

    pub fn select(
        self: &Arc<Self>,
        start_utf16_codeunit: u32,
        end_utf16_codeunit: u32,
    ) -> Arc<ComposerUpdate> {
        let start = wysiwyg::Location::from(
            usize::try_from(start_utf16_codeunit).unwrap(),
        );
        let end = wysiwyg::Location::from(
            usize::try_from(end_utf16_codeunit).unwrap(),
        );

        Arc::new(ComposerUpdate::from(
            self.inner.lock().unwrap().select(start, end),
        ))
    }

    pub fn replace_text(
        self: &Arc<Self>,
        new_text: String,
    ) -> Arc<ComposerUpdate> {
        Arc::new(ComposerUpdate::from(
            self.inner
                .lock()
                .unwrap()
                .replace_text(Utf16String::from_str(&new_text)),
        ))
    }

    pub fn replace_text_in(
        self: &Arc<Self>,
        new_text: String,
        start: u32,
        end: u32,
    ) -> Arc<ComposerUpdate> {
        let start = usize::try_from(start).unwrap();
        let end = usize::try_from(end).unwrap();
        Arc::new(ComposerUpdate::from(
            self.inner.lock().unwrap().replace_text_in(
                Utf16String::from_str(&new_text),
                start,
                end,
            ),
        ))
    }

    pub fn backspace(self: &Arc<Self>) -> Arc<ComposerUpdate> {
        Arc::new(ComposerUpdate::from(self.inner.lock().unwrap().backspace()))
    }

    pub fn delete(self: &Arc<Self>) -> Arc<ComposerUpdate> {
        Arc::new(ComposerUpdate::from(self.inner.lock().unwrap().delete()))
    }

    pub fn delete_in(
        self: &Arc<Self>,
        start: u32,
        end: u32,
    ) -> Arc<ComposerUpdate> {
        let start = usize::try_from(start).unwrap();
        let end = usize::try_from(end).unwrap();
        Arc::new(ComposerUpdate::from(
            self.inner.lock().unwrap().delete_in(start, end),
        ))
    }

    pub fn enter(self: &Arc<Self>) -> Arc<ComposerUpdate> {
        Arc::new(ComposerUpdate::from(self.inner.lock().unwrap().enter()))
    }

    pub fn bold(self: &Arc<Self>) -> Arc<ComposerUpdate> {
        Arc::new(ComposerUpdate::from(self.inner.lock().unwrap().bold()))
    }

    pub fn italic(self: &Arc<Self>) -> Arc<ComposerUpdate> {
        Arc::new(ComposerUpdate::from(self.inner.lock().unwrap().italic()))
    }

    pub fn strike_through(self: &Arc<Self>) -> Arc<ComposerUpdate> {
        Arc::new(ComposerUpdate::from(
            self.inner.lock().unwrap().strike_through(),
        ))
    }

    pub fn underline(self: &Arc<Self>) -> Arc<ComposerUpdate> {
        Arc::new(ComposerUpdate::from(self.inner.lock().unwrap().underline()))
    }

    pub fn inline_code(self: &Arc<Self>) -> Arc<ComposerUpdate> {
        Arc::new(ComposerUpdate::from(
            self.inner.lock().unwrap().inline_code(),
        ))
    }

    pub fn ordered_list(self: &Arc<Self>) -> Arc<ComposerUpdate> {
        Arc::new(ComposerUpdate::from(
            self.inner.lock().unwrap().ordered_list(),
        ))
    }

    pub fn unordered_list(self: &Arc<Self>) -> Arc<ComposerUpdate> {
        Arc::new(ComposerUpdate::from(
            self.inner.lock().unwrap().unordered_list(),
        ))
    }

    pub fn undo(self: &Arc<Self>) -> Arc<ComposerUpdate> {
        Arc::new(ComposerUpdate::from(self.inner.lock().unwrap().undo()))
    }

    pub fn redo(self: &Arc<Self>) -> Arc<ComposerUpdate> {
        Arc::new(ComposerUpdate::from(self.inner.lock().unwrap().redo()))
    }

    pub fn set_link(self: &Arc<Self>, link: String) -> Arc<ComposerUpdate> {
        let link = Utf16String::from_str(&link);
        Arc::new(ComposerUpdate::from(
            self.inner.lock().unwrap().set_link(link),
        ))
    }

    pub fn indent(self: &Arc<Self>) -> Arc<ComposerUpdate> {
        Arc::new(ComposerUpdate::from(self.inner.lock().unwrap().indent()))
    }

    pub fn un_indent(self: &Arc<Self>) -> Arc<ComposerUpdate> {
        Arc::new(ComposerUpdate::from(self.inner.lock().unwrap().unindent()))
    }

    pub fn to_tree(self: &Arc<Self>) -> String {
        self.inner.lock().unwrap().to_tree().to_string()
    }

    pub fn get_current_dom_state(self: &Arc<Self>) -> ComposerState {
        self.inner
            .lock()
            .unwrap()
            .get_current_state()
            .clone()
            .into()
    }

    pub fn action_states(
        self: &Arc<Self>,
    ) -> HashMap<ComposerAction, ActionState> {
        let inner = self.inner.lock().unwrap();
        build_composer_action_states(&inner)
    }
}

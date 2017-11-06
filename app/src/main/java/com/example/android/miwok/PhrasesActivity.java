/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class PhrasesActivity extends VocabularyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        // Add words and their translations to the numbers list
        final ArrayList<Word> englishWords = new ArrayList<Word>();
        englishWords.add(new Word("minto wuksus?", "Where are you going?", R.raw.phrase_where_are_you_going));
        englishWords.add(new Word("tinnә oyaase'nә?", "What is your name?", R.raw.phrase_what_is_your_name));
        englishWords.add(new Word("oyaaset...", "My name is...", R.raw.phrase_my_name_is));
        englishWords.add(new Word("michәksәs?", "How are you feeling?", R.raw.phrase_how_are_you_feeling));
        englishWords.add(new Word("kuchi achit.", "I’m feeling good.", R.raw.phrase_im_feeling_good));
        englishWords.add(new Word("әәnәs'aa?", "Are you coming?", R.raw.phrase_are_you_coming));
        englishWords.add(new Word("hәә’ әәnәm", "Yes, I am comming.", R.raw.phrase_yes_im_coming));
        englishWords.add(new Word("әәnәm", "I’m coming.", R.raw.phrase_im_coming));
        englishWords.add(new Word("yoowutis", "Let’s go.", R.raw.phrase_lets_go));
        englishWords.add(new Word("әnni'nem", "Come here.", R.raw.phrase_come_here));

        // Use a custom ArrayAdapter to fill the ListView with word items
        WordAdapter adapter = new WordAdapter(this, englishWords, R.color.category_phrases);
        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Word word = englishWords.get(position);

                // Release the resources and abandon AudioFocus
                releaseMediaPlayer();

                final int result = audioManager.requestAudioFocus(mAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // And play
                    mediaPlayer = MediaPlayer.create(PhrasesActivity.this, word.getSoundResourceId());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
    }
}

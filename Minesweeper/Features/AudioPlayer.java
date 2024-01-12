package Features;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.*;

public class AudioPlayer {
	private Clip effects; //effect sound
    private Clip[] songs; //list of song
    
	private int currentSongId; //current song in a list song
	private float volume = 0.8f; //volume of sound
	private boolean songMute, effectMute; //state of mute
    private int type; //type to choose between song (1) or effect sound (2)

	public AudioPlayer(String name,int type) {
        if (type == 1)
		    loadSongs();
        if (type == 2){
		    effects = getClip(name);
            updateEffectsVolume();
        }
        this.type = type;    

    }


	private void loadSongs() {
		String[] names = {"soundtrack"};
		songs = new Clip[names.length];
		for (int i = 0; i < songs.length; i++){
			songs[i] = getClip(names[i]);
        }
	}

	private Clip getClip(String name) {
		URL url = getClass().getResource("/sound/"+ name +".wav");
		AudioInputStream audio;

		try {
			audio = AudioSystem.getAudioInputStream(url);
			Clip c = AudioSystem.getClip();
			c.open(audio);
			return c;

		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}

		return null;

	}

	public void setVolume(float volume) {
		this.volume = volume;
        if (type == 1)
	    	updateSongVolume();
        if (type == 2)
		    updateEffectsVolume();
	}

	public void stopSong() {
		if (songs[currentSongId].isActive())
			songs[currentSongId].stop();
	}

	public void playEffect() {
		effects.setMicrosecondPosition(0);
		effects.start();
	}

	public void playSong(int song) {
		stopSong();

		currentSongId = song;
		updateSongVolume();
		songs[currentSongId].setMicrosecondPosition(0);
		songs[currentSongId].loop(Clip.LOOP_CONTINUOUSLY);
	}

	public void toggleSongMute() {
		this.songMute = !songMute;
        for(Clip c : songs){
            BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(songMute);
        }
	}

	public void toggleEffectMute() {
		this.effectMute = !effectMute;
		if (type == 2){
			BooleanControl booleanControl = (BooleanControl) effects.getControl(BooleanControl.Type.MUTE);
			booleanControl.setValue(effectMute);
		}
	}

	private void updateSongVolume() {
		FloatControl gainControl = (FloatControl) songs[currentSongId].getControl(FloatControl.Type.MASTER_GAIN);
		float range = gainControl.getMaximum() - gainControl.getMinimum();
		float gain = (range * (float) volume) + gainControl.getMinimum();
		gainControl.setValue(gain);
	}

	private void updateEffectsVolume() {
		FloatControl gainControl = (FloatControl) effects.getControl(FloatControl.Type.MASTER_GAIN);
		float range = gainControl.getMaximum() - gainControl.getMinimum();
		float gain = (range * volume) + gainControl.getMinimum();
		gainControl.setValue(gain);
	}

	public float getVolume(){
		return volume;
	}

	public boolean getSongMute(){
		return songMute;
	}

	public boolean getEffectMute(){
		return effectMute;
	}

	public Clip getEffects(){
		return effects;
	}

	public int getType(){
		return type;
	}
}

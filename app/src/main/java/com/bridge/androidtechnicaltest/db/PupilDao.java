package com.bridge.androidtechnicaltest.db;

import com.bridge.androidtechnicaltest.models.Pupil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface PupilDao {
	
	@Query("SELECT * FROM Pupils ORDER BY name ASC")
	LiveData<List<Pupil>> getPupils();
	
	@Query("SELECT * FROM Pupils WHERE pupil_id = :pupilId")
	LiveData<Pupil> getPupil(@NonNull Long pupilId);
	
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void saveOrUpdatePupils(@NonNull List<Pupil> pupils);
	
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void saveOrUpdatePupil(@NonNull Pupil pupil);
}
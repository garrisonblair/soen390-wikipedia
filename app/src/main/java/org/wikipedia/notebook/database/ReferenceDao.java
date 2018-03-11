package org.wikipedia.notebook.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Andres on 2018-03-08.
 */
@Dao
public interface ReferenceDao {

    @Query("SELECT * FROM `references` WHERE articleId = :articleId ORDER BY noteId ASC")
    public List<ReferenceEntity> getAllArticleReferences(int articleId);

    @Insert
    public void addReferences(List<ReferenceEntity> references);

}

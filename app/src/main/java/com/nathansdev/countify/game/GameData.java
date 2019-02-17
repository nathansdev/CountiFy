package com.nathansdev.countify.game;

import android.os.Parcelable;
import com.google.auto.value.AutoValue;

import java.util.List;

/**
 * Encapsulates Game information to transfer between fragments.
 */
@AutoValue
public abstract class GameData implements Parcelable {

    public abstract int target();

    public abstract List<Integer> randomNumbers();

    public static Builder builder() {
        return new AutoValue_GameData.Builder();
    }


    public abstract Builder toBuilder();

    /**
     * @return a builder with all properties of this class pre populated.
     */
    public Builder withBuild() {
        return toBuilder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract GameData build();

        public abstract Builder target(int i);

        public abstract Builder randomNumbers(List<Integer> i);
    }
}

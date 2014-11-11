package ru.ifmo.md.colloquium2;

/**
 * Created by sultan on 11.11.14.
 */
public class VoteMan {

    private long id;
    private String name;
    private int votes;

    public VoteMan() {
        id = -1;
        votes = 0;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public void setName(String name) {

        this.name = name;
    }

    public void setId(long id) {

        this.id = id;
    }

    public int getVotes() {

        return votes;
    }

    public String getName() {

        return name;
    }

    public long getId() {

        return id;
    }
}

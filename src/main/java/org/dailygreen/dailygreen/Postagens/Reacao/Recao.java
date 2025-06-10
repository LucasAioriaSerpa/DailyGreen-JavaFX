package org.dailygreen.dailygreen.Postagens.Reacao;

import java.util.Arrays;

public class Recao {
    private final long id_reacao;
    private String reaction;
    private String[] reactionsType;
    private int[] reactionNum;
    public Recao(long id_reacao, String reaction) {
        this.id_reacao = id_reacao;
        this.reaction = reaction;
        reactionsType = new String[]{
                "gostei",
                "parbens",
                "apoio",
                "amei",
                "genial"
        };
        reactionNum = new int[]{0,0,0,0,0};
    }
    // ? GETTER AND SETTER
    public String getReaction() {return reaction;}
    public void setReaction(String reaction, char addSub) {
        if (addSub == '+') {
            int index = Arrays.asList(reactionsType).indexOf(reaction);
            if (index != -1) {reactionNum[index]++;}
        } else if (addSub == '-') {
            int index = Arrays.asList(reactionsType).indexOf(reaction);
            if (index != -1) {reactionNum[index]--;}
        }
    }

    public String[] getReactionsType() {return reactionsType;}
    public void setReactionsType(String[] reactionsType) {this.reactionsType = reactionsType;}

    public int[] getReactionNum() {return reactionNum;}
    public void setReactionNum(int[] reactionNum) {this.reactionNum = reactionNum;}

}

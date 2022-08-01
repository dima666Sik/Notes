package guru.work.prog.iface;

public interface Randomize {
    default int generateRandomize(int min, int max){
        return (int)Math.floor(Math.random()*(max-min+1)+min);
    }
}

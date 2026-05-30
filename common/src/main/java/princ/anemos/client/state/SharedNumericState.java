package princ.anemos.client.state;

public abstract class SharedNumericState<T extends Number> {
    public T min;
    public T max;
    public T current;
    public T start;
    public T end;
    public T progress;
    public T transitionDuration;
    public boolean execTransition;

    public SharedNumericState(T min, T max, T current, T start, T end, T progress, T transitionDuration, boolean execTransition) {
        this.min = min;
        this.max = max;
        this.current = current;
        this.start = start;
        this.end = end;
        this.progress = progress;
        this.transitionDuration = transitionDuration;
        this.execTransition = execTransition;
    }

    public abstract void resolveValues();
}

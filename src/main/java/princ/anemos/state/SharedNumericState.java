package princ.anemos.state;


public abstract class SharedNumericState<T extends Number> {
    public T min;
    public T max;
    public T targetVal;
    public int transitionTime;
    public int elapsedTransitionTime;
    public boolean execTransition;

    public SharedNumericState(T min, T max, T targetVal, int transitionTime, int elapsedTransitionTime, boolean execTransition) {
        this.min = min;
        this.max = max;
        this.targetVal = targetVal;
        this.transitionTime = transitionTime;
        this.elapsedTransitionTime = elapsedTransitionTime;
        this.execTransition = execTransition;
    }

    public abstract void resolveValues();
}

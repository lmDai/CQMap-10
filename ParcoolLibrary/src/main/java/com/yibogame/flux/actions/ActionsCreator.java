package com.yibogame.flux.actions;


import com.yibogame.flux.dispatcher.Dispatcher;

/**
 * Created by lgvalle on 02/08/15.
 */
public class ActionsCreator {

    private static ActionsCreator instance;
    final Dispatcher dispatcher;

    public ActionsCreator(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    /***
     * this is unused
     * @param dispatcher
     * @return
     */
    private static ActionsCreator getInstance(Dispatcher dispatcher) {
        if (instance == null) {
            instance = new ActionsCreator(dispatcher);
        }
        return instance;
    }

    protected Dispatcher getDispatcher(){
        return dispatcher;
    }

//    public void create(String text) {
//        dispatcher.dispatch(
//                TodoActions.TODO_CREATE,
//                TodoActions.KEY_TEXT, text
//        );
//
//    }
//
//    public void destroy(long id) {
//        dispatcher.dispatch(
//                TodoActions.TODO_DESTROY,
//                TodoActions.KEY_ID, id
//        );
//    }
//
//    public void undoDestroy() {
//        dispatcher.dispatch(
//                TodoActions.TODO_UNDO_DESTROY
//        );
//    }
//
//    public void toggleComplete(Todo todo) {
//        long id = todo.getId();
//        String actionType = todo.isComplete() ? TodoActions.TODO_UNDO_COMPLETE : TodoActions.TODO_COMPLETE;
//
//        dispatcher.dispatch(
//                actionType,
//                TodoActions.KEY_ID, id
//        );
//    }
//
//    public void toggleCompleteAll() {
//        dispatcher.dispatch(TodoActions.TODO_TOGGLE_COMPLETE_ALL);
//    }
//
//    public void destroyCompleted() {
//        dispatcher.dispatch(TodoActions.TODO_DESTROY_COMPLETED);
//    }
}

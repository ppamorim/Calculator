package com.ppamorim.calculator.service.internal;

import android.content.Context;
import android.widget.Toast;
import com.joanzapata.android.asyncservice.api.annotation.ApplicationContext;
import com.joanzapata.android.asyncservice.api.annotation.AsyncService;
import com.joanzapata.android.asyncservice.api.annotation.CacheThenCall;
import com.joanzapata.android.asyncservice.api.annotation.ErrorManagement;
import com.joanzapata.android.asyncservice.api.annotation.Init;
import com.joanzapata.android.asyncservice.api.annotation.Mapping;
import com.joanzapata.android.asyncservice.api.annotation.Null;
import com.joanzapata.android.asyncservice.api.annotation.Ui;
import com.ppamorim.calculator.core.service.BaseQuery;
import com.ppamorim.calculator.model.History;
import com.ppamorim.calculator.utils.DummyErrorMessage;
import com.ppamorim.calculator.utils.event.NoUserEvent;
import com.ppamorim.calculator.utils.event.UserEvent;
import java.util.ArrayList;
import java.util.List;

@AsyncService
public class HistoryQuery extends BaseQuery {


  /*
If you need a context you can inject
the application context here.
*/
  @ApplicationContext
  static Context applicationContext;
  @Init
  static void initStatic() {
    // Executed once for all services
  }
  @Init
  static void init() {
    // Executed once for this service
  }
  /*
  By default, methods are executed in a background thread.
  No caching is involved.
  */
  @Null(NoUserEvent.class)
  @ErrorManagement(@Mapping(on = 200, send = DummyErrorMessage.class))
  public UserEvent getHistoryAsync(Long id) {
    List<History> histories = (List<History>)getAllFromModel(History.class);
    return new UserEvent(id, 200, histories);
  }

  /*
  By default, methods are executed in a background thread.
  No caching is involved.
  */

  public void addHistoryAsync(float count) {
    History history = new History(count);
    history.save();
  }

  public void removeHistoryAsync(int id) {
    History history = (History)getItemFromModel(History.class, id);
    history.delete();
  }

  /*
  If you use @Cached, the result is cached before being sent to the receiver(s).
  On next call, receivers will immediately receive the previous cached event, then
  this method is called. Its new result replaces the previous cache, then is sent
  to the receivers.
  The default cache key is <class_name>.<method_name>(<arg1.toString>, <arg2.toString>, ...)
  */
  @CacheThenCall
  public UserEvent getUserAsyncWithCache(Long id) {
    sleep();
    displayMessage("This is a toast displayed from the DemoService.");
    return new UserEvent(id, "Joan", 25);
  }
  @Ui
  protected void displayMessage(String message) {
    Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show();
  }
  // Private methods are not overridden, so you can call them directly.
  private void sleep() {
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}

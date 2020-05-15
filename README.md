# Coroutine Exceptions Handling 

Observations from Experimenting with Coroutines Exceptions 

* If Child job throws in Exception it causes the following 
  * Cancel that job 
  * Cancel any job after it ( Essentially Everything is canceled)
  * Cancel the parent job 

* If Child job cancels itself with cancel() from (inside the job)
  * Nothing Happens 
  * That job is a Success 
  * All child job runs successfully 
  * Parent is a success 
  * Parent do not throw any exceptions 

* cancel() can only be used only on job itself ( outside the job. For example job.cancel())

* If a Cancellation Exception is thrown
  * That job will be cancelled 
  * Other jobs will be allowed to run 
  * Parent Job is a success 
  * Parent do not  throw any exception 

* If Supervisor Jobs are used 
  * That job will be cancelled 
  * Other jobs will be allowed to run 
  * Parent job is not a success 
  * Parent throws an exception 
  
Reference : https://www.youtube.com/watch?v=KWocgiYwwmM 



 Objects:
     Event:
      -passive
      -created by Organizer
      - have title, theme
      - must be scheduled in time with duration
      - must have assigned location
      - have minimal number of participnts = N
      - can have costs 
      - can be cancelled // nice-to-have
      - event is scheduled, when
          participants[>=N] agree with time and location.
          location is agree with schedule and cost.          

     Location:
      -active
      -have capacity,
      -schedule
      -coordinates 
      -cost.

      Activities:
        - assign request to event (pre-booking) at time at cost
        - remove prebooking
        - confirm prebooked event  [ booked ]
        - cancel booked [ nice to have ]
        - actually run event. [Nothing to show]

      Law:
        - two events at the same time can not overlapp.

     Participant:
        - active 
        - email/im id[gitter,slack]/phone
        - login/password 

        Activities:
        - respond to events on selected themes
           - is this event is interest ?
           - can it be scheduled at given location at given time ?
           - confirm interest to event by payment.

           - withdrow interest [nice-to-have]
           
           - actually participate in event. [Nothing to show]

     Organizer:
        - active.
        - email/im id[gitter,slack]/phone
        - login/password 

        Activities:
        - create event project and initial set of schedules 
        - set event cost in addition to location cost

        - withdrow or run event in given location.

     System:
        - registration/deregistration of participants in  system.
        - registration/deregistration of organizers in  system.
        - keep list of location



------------
  Locations:  static list     
  



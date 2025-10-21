```
  ___ ___  _ __ ___  _ __ ___   ___  _ __  ___ 
 / __/ _ \| '_ ` _ \| '_ ` _ \ / _ \| '_ \/ __|
| (_| (_) | | | | | | | | | | | (_) | | | \__ \
 \___\___/|_| |_| |_|_| |_| |_|\___/|_| |_|___/
```

## 3.0.0

1. reworked **Storage** into extending **ParticipantRepository** and **ObservationRepository** which contains methods
   declared previously in **Storage**
2. added interface default method _describe_ to that returns name of the interface and it's extensions
3. added tests to check **Storage**, **ParticipantRepository** and **ObservationRepository** default methods
4. moved **JsonUtilsTest** to utils package
5. moved interfaces to storage package

## 2.0.0

1. moved **inMemoryDB** and **inMemoryDBTest** to separate module
2. moved **Storage** interface to module root
3. removed db part from model path

## 1.0.1

1. added missing _getParticipants_ and _getObservations_ methods to **Storage** interface
2. removed unused _getters_ from **InMemoryDB**
3. added changelog

## 1.0.0

1. added **Storage** interface
2. refactored **InMemoryDB** to implement **Storage**
3. added unittests for **InMemoryDB** and **JsonUtils**

---

## 0.0.1-SNAPSHOT

1. initial release

---
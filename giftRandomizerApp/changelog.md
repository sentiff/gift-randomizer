```
       _  __ _  ______                _                 _               ___              
      (_)/ _| | | ___ \              | |               (_)             / _ \             
  __ _ _| |_| |_| |_/ /__ _ _ __   __| | ___  _ __ ___  _ _______ _ __/ /_\ \_ __  _ __  
 / _` | |  _| __|    // _` | '_ \ / _` |/ _ \| '_ ` _ \| |_  / _ \ '__|  _  | '_ \| '_ \ 
| (_| | | | | |_| |\ \ (_| | | | | (_| | (_) | | | | | | |/ /  __/ |  | | | | |_) | |_) |
 \__, |_|_|  \__\_| \_\__,_|_| |_|\__,_|\___/|_| |_| |_|_/___\___|_|  \_| |_/ .__/| .__/ 
  __/ |                                                                     | |   | |    
 |___/                                                                      |_|   |_|    
                                                                            |_|   |_|
```

## 0.1.1

1. added basic logging to all controllers
2. changed variable names from _dbResponse_ to _response_ and _response_ to _participant/observation_ in
   **GiftRandomizerController** and **ParticipantController**
3. added _final_ modifier to dependencies in **GiftRandomizerController** and **ParticipantController**
4. added _application-{env}.properties_ to the resource dir with default profile set to _dev_

## 0.1.0

1. upgraded **commons** dependency from **2.0.0** to **3.0.0**
2. upgraded **memoryDB** dependency from **1.0.1** to **1.1.0**
3. changed mapping for _updateParticipantById_ to from **POST** to **PUT**, changed mappings for
   _updateParticipantNameById_
   _updateParticipantGiftIdeasById_ from **POST** to **PATCH**
4. changed dependency injection in **GiftRandomizerController** and **ParticipantController** from _@Autowired_
   annotation to constructor based
5. added static modifier to UNKNOWN_ERROR constant in **ParticipantController**
6. changed _getMemoryDB_ bean name to streamline with memoryDB module naming scheme

## 0.0.4

1. changed to use new **memoryDB** module in place of commons **InMemoryDB**
2. fixed ascii in changelog

---

## 0.0.3

1. changed **InMemoryDB** beans from **InMemoryDB** to **Storage** class
2. added changelog

---

## 0.0.2-SNAPSHOT

1. moved **model classes**, **InMemoryDB**, **exceptions** and **JsonUtils** to commons module

---

## 0.0.1-SNAPSHOT

1. initial release

---
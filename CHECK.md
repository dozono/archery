
# New Entity

- Deferred Registry for entity
  - Reg this in FML mod event bus
- Register entity in Deferred registry
- Register entity renderer
  - Use FMLClientSetupEvent to register
- Create Entity class
  - Override getAddEntityPacket to sent packet if need


# New Item

- Deferred Registry
  - Reg this in FML mod event bus
- Register item in Deferred registry
- Register tag if need
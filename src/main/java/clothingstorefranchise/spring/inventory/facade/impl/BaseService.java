package clothingstorefranchise.spring.inventory.facade.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import clothingstorefranchise.spring.common.types.IEntityDto;

public abstract class BaseService
	<TEntity , TId, TRepository extends JpaRepository<TEntity, TId>> {
	//, TEntityDto extends IEntityDto<TId>
	@Autowired
	protected ModelMapper modelMapper;
	
	public TRepository repository;
	
	private final Class<TEntity> entityClass;
	
	public BaseService(Class<TEntity> entityClass, TRepository repository) {
		
        this.entityClass = entityClass;
        this.repository = repository;
   }
	
	public <TEntityDto extends IEntityDto<TId>> TEntity createBase(TEntityDto entityDto) {
		TEntity entity = map(entityDto, entityClass);
		 return createAction(entity);
	}
	
	protected TEntity createAction(TEntity entity) {
		return repository.save(entity);
	}
	
	public <TEntityDto extends IEntityDto<TId>> List<TEntity> createBase(List<TEntityDto> dtos) {
		List<TEntity> entities = mapList(dtos, entityClass);
		return createAction(entities);
	}
	
	protected List<TEntity> createAction(List<TEntity> entities) {
		return repository.saveAll(entities);
	}
	
	public TEntity loadBase(TId id) {
		//error control
		TEntity entity = repository.findById(id).get();
		return entity;
	}
	
	public List<TEntity> loadAllBase(){
		return repository.findAll();
	}
	
	public <TEntityDto extends IEntityDto<TId>> TEntity updateBase(TEntityDto entityDto) {
		
		TEntity entity=loadBase(entityDto.key());
		map(entityDto, entity);
		return repository.save(entity);
	}
	
	public void delete(TId id) {
		repository.deleteById(id);
	}
	
	protected <TSource, TTarget> TTarget map(TSource source, Class<TTarget> targetClass) {
	    return modelMapper.map(source, targetClass);
	}
	
	protected <TSource, TTarget> TTarget map(TSource source, TTarget target) {
	    modelMapper.map(source, target);
	    return target;
	}
	
	protected <TSource, TTarget> List<TTarget> mapList(List<TSource> source, Class<TTarget> targetClass) {
	    return source
	      .stream()
	      .map(element -> modelMapper.map(element, targetClass))
	      .collect(Collectors.toList());
	}
}

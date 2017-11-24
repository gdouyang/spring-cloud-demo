package spring.cloud;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.google.common.collect.Maps;

import rx.Observable;
import rx.Observer;

@RestController
public class AggregationController {
	Logger LOGGER = LoggerFactory.getLogger(AggregationController.class);
	@Autowired
	private AggregationService aggregationService;

	/**
	 * 使用zuul做聚合
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("aggregate/{id}")
	public DeferredResult<HashMap<String, User>> aggregate(@PathVariable Long id) {
		Observable<HashMap<String, User>> result = this.aggregateObservable(id);
		return this.toDeferredResult(result);
	}

	/**
	 * <code>
	 * Observable.zip(this.aggregationService.getUserById(id), this.aggregationService.getMovieUserById(id),
				new Func2<User, User, HashMap<String, User>>() {
					&#64;Override
					public HashMap<String, User> call(User user, User movieUser) {
	
						HashMap<String, User> map = Maps.newHashMap();
						map.put("user", user);
						map.put("movieUser", movieUser);
						return map;
					}
				});
	 * </code>
	 * 
	 * @param id
	 * @return
	 */
	public Observable<HashMap<String, User>> aggregateObservable(Long id) {
		return Observable.zip(this.aggregationService.getUserById(id), this.aggregationService.getMovieUserById(id),
				(user, movieUser) -> {
					HashMap<String, User> map = Maps.newHashMap();
					map.put("user", user);
					map.put("movieUser", movieUser);
					return map;
				});
	}

	public DeferredResult<HashMap<String, User>> toDeferredResult(Observable<HashMap<String, User>> details) {
		DeferredResult<HashMap<String, User>> result = new DeferredResult<>();
		details.subscribe(new Observer<HashMap<String, User>>() {

			@Override
			public void onCompleted() {
				LOGGER.info("完成...");
			}

			@Override
			public void onError(Throwable e) {
				LOGGER.info("发生错误...", e);
			}

			@Override
			public void onNext(HashMap<String, User> movieDetails) {
				LOGGER.info("onNext");
				result.setResult(movieDetails);
			}

		});
		return result;
	}

}

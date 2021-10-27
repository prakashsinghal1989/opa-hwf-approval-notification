/**
 * This file will be used for all API calls.
 */
import axios from 'axios';
import queryString from 'query-string';

import networkConstants from '../constants/networkConstants';

export default class NetworkUtils {
	static env = process.env.REACT_APP_BUILD_ENV || 'default';

	// static networkParams = {
	// 	serverBaseUrl: baseUrl,
	// };

	static memoizedData = {};

	/**
	 * @method handleErrors This method will be invoked when the api calls throw an error and will construct the errorObj containing errorCode and error message based on the error Code recieved.
	 * @param {*} err Object: {response : {status = '', errors ; []}}
	 */
	static handleErros(err) {
		const { response: { status = '' } = {} } = err;

		const {
			UNAUTHORIZED_MESSAGE,
			BAD_REQUEST_MESSAGE,
			FORBIDDEN_MESSAGE,
			NOT_FOUND_MESSAGE,
			REQUEST_TIMED_OUT,
			SERVER_ERROR,
			BAD_GATEWAY,
			GATEWAY_TIMEOUT,
			GENERIC_ERROR,
		} = networkConstants;

		const errorObject = {
			status,
			errorMessage: GENERIC_ERROR, // default error Message
		};

		switch (status) {
			case 400:
				errorObject.errorMessage = BAD_REQUEST_MESSAGE;
				break;
			case 401:
				errorObject.errorMessage = UNAUTHORIZED_MESSAGE;
				break;
			case 403:
				errorObject.errorMessage = FORBIDDEN_MESSAGE;
				break;
			case 404:
				errorObject.errorMessage = NOT_FOUND_MESSAGE;
				break;
			case 408:
				errorObject.errorMessage = REQUEST_TIMED_OUT;
				break;
			case 500:
				errorObject.errorMessage = SERVER_ERROR;
				break;
			case 502:
				errorObject.errorMessage = BAD_GATEWAY;
				break;
			case 503:
				errorObject.errorMessage = GATEWAY_TIMEOUT;
				break;
			default:
				break;
		}

		return errorObject;
	}

	/**
	 * @method makeApiRequest This method is a wrapper on all network calls. If any kind
	 * of customization is required, that should be done here
	 * @param {*} requestObject
	 * requestObject will include below attributes
	 * requuestObject  = {
	 *    method,   // The type of request, GET, POST, PUT, DELETE etc
	 *    url,  //The url needs to be called
	 *    data, // The data needs to be passed to server
	 *    params, // For get request, if any parameter need to be passed along with URL
	 *    memoizeResponse, // If true, it will remember the response and next time it won't make
	 *    isExternal, //  boolean: If the network call is third-party call
	 *    skipHeader, // boolean: If the auth token needs to be skipped for any request
	 * }
	 */

	static makeApiRequest(requestObject) {
		const { method, url, data = {}, params = undefined, memoizeResponse = false } = requestObject;

		// const { serverBaseUrl } = this.networkParams;
		/* eslint-disable consistent-return */
		return new Promise((resolve, reject) => {
			try {
				//  const completeUrl = isExternal ? url : `${serverBaseUrl}/${url}`;

				if (this.memoizedData[url]) {
					return resolve(this.memoizedData[url]);
				}
				const headerObject = {
					'content-type': 'application/json',
				};
				axios({
					method,
					url,
					data,
					params,
					headers: headerObject,
					paramsSerializer: (queryParams) =>
						queryString.stringify(queryParams, {
							arrayFormat: 'repeat',
						}),
				})
					.then((response) => {
						const { data: responseData, status } = response;
						if (memoizeResponse) {
							this.memoizedData[url] = { responseData, status };
						}
						return resolve({ responseData, status });
					})
					.catch((err) => {
						const errorObject = this.handleErros(err);
						return reject(errorObject);
					});
			} catch (e) {
				return reject(e);
			}
		});
	}
}

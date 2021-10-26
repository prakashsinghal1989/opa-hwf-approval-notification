import * as Yup from 'yup';

export default {
	purpose: Yup.string().trim().required('Please enter purpose of this expense report'),
	date: Yup.string().trim().required('Please select expense date'),
	type: Yup.string().trim().required('Please select expense type'),
	currency: Yup.string().trim().required('Please select expense currency'),
	amount: Yup.string()
		.required('Please enter amount')
		.matches(/^[0-9]/, 'please enter only digits'),
};

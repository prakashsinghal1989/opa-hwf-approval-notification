/**
 * Button Component
 */
import { node, string, bool, oneOf } from 'prop-types';
import clsx from 'clsx';

// Styles
import './styles.scss';

export const iconPositionMap = {
	right: 'right',
	left: 'left',
};

function Button({
	additionalClasses,
	appearance,
	size,
	children,
	disabled,
	iconName,
	iconPosition,
	...props
}) {
	const getIcon = (icon) => {
		return <i className={`fas fa-${icon} opa-button--icon-${iconPosition}`} />;
	};
	const getChildren = () => {
		// if Icon is also present
		if (iconName) {
			// For Icon Position as Left
			if (iconPosition === iconPositionMap.left) {
				return (
					<>
						{getIcon(iconName)}
						{children}
					</>
				);
			}
			// For Icon Position as right , which can be default as well
			return (
				<>
					{children}
					{getIcon(iconName)}
				</>
			);
		}
		// return just the children as the Icon is not present
		return children;
	};

	return (
		<button
			type="button"
			className={clsx(
				additionalClasses,
				appearance,
				size,
				'opa-button',
				{
					disabled,
				},
				{
					'button-icon-only': !children,
				},
			)}
			disabled={disabled}
			data-testid="button"
			{...props}
		>
			{getChildren()}
		</button>
	);
}

Button.propTypes = {
	children: node.isRequired,
	disabled: bool,
	additionalClasses: string,
	appearance: oneOf(['primary', 'secondary', 'link']),
	size: oneOf(['medium', 'large', 'small']),
	iconPosition: oneOf(['left', 'right']),
	iconName: string,
};

Button.defaultProps = {
	disabled: false,
	additionalClasses: '',
	appearance: 'primary',
	size: 'medium',
	iconPosition: 'left',
	iconName: '',
};

export default Button;

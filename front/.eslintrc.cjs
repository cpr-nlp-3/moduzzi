module.exports = {
	root: true,
	env: {
		es2020: true,
		browser: true,
		node: true,
	},
	extends: [
		"airbnb",
		"plugin:import/errors",
		"plugin:import/warnings",
		"plugin:prettier/recommended",
		"plugin:@typescript-eslint/recommended",
		"prettier",
	],
	ignorePatterns: ["dist", ".eslintrc.cjs"],
	parser: "@typescript-eslint/parser",
	plugins: ["@typescript-eslint", "prettier"],
	rules: {
		"react-refresh/only-export-components": [
			"warn",
			{ allowConstantExport: true },
		],
		"react/jsx-filename-extension": ["warn", { extensions: [".tsx"] }],
		"prettier/prettier": [
			"error",
			{
				endOfLine: "auto",
			},
		],
		"react/react-in-jsx-scope": "off",
	},
};

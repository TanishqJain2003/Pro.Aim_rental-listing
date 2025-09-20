import React from 'react';

type GoogleMapEmbedProps = {
	/** Optional text query like a city or address. Example: "New York, NY" */
	placeQuery?: string;
	/** Optional coordinates; used when placeQuery is not provided */
	latitude?: number;
	longitude?: number;
	/** Zoom level for the map; defaults to 12 when using coordinates */
	zoom?: number;
	/** Height of the map container (Tailwind class). Defaults to h-64 */
	heightClassName?: string;
};

const GoogleMapEmbed: React.FC<GoogleMapEmbedProps> = ({
	placeQuery,
	latitude,
	longitude,
	zoom = 12,
	heightClassName = 'h-64',
}) => {
	let src = '';

	if (placeQuery && placeQuery.trim().length > 0) {
		src = `https://www.google.com/maps?q=${encodeURIComponent(placeQuery)}&output=embed`;
	} else if (
		typeof latitude === 'number' &&
		typeof longitude === 'number'
	) {
		src = `https://www.google.com/maps?q=${latitude},${longitude}&hl=en&z=${zoom}&output=embed`;
	} else {
		// Fallback to a broad world view if nothing provided
		src = 'https://www.google.com/maps?q=0,0&z=1&output=embed';
	}

	return (
		<div className={`w-full rounded-lg overflow-hidden border border-secondary-200 ${heightClassName}`}>
			<iframe
				title="Google Map"
				src={src}
				className="w-full h-full border-0"
				loading="lazy"
				referrerPolicy="no-referrer-when-downgrade"
				allowFullScreen
			/>
		</div>
	);
};

export default GoogleMapEmbed;



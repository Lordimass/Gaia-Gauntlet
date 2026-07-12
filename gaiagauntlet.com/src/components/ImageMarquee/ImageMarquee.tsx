import "./ImageMarquee.css"
import Image from "next/image"

interface Props {
    images: string[];
}

export default function ImageMarquee({ images }: Props) {
    const duplicatedImages = [...images, ...images];

    return (
        <div className="wrapper-marquee">
            <div className="marquee-border" id="top-marquee-border" aria-hidden="true"/>
            <div className={"track"}>
                {duplicatedImages.map((src, index) => (
                    <div key={index} className={"item"}>
                        <Image
                            src={src}
                            alt=""
                            width={640}
                            height={360}
                        />
                    </div>
                ))}
            </div>
            <div className="marquee-border" id="bottom-marquee-border" aria-hidden="true"/>
        </div>
    );
}
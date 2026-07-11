import "./Creators.css"
import Image from "next/image";
import creators from "../../../public/json/gg_team.json"

export default function Creators() {

    return <div className={"creators"}>
        {creators.people.map(creator => {
            console.log(creator);
            return <div className={"creator"}>
            <div className={"creator-img-container"}>
                <Image
                    src={creator.image}
                    width={300}
                    height={300}
                    alt={creator.name}
                />
            </div>
            <div className={"creator-info"}>
                <h2>{creator.name}</h2>
                {creator.quote ? <p className="quote">"{creator.quote}"</p> : null}

                {creator.tags ? <div className="tags">
                    {creator.tags.map(tag => <span key={tag}>{tag}</span>)}
                </div> : null}

                {creator.socials ? <div className="socials">
                    {creator.socials.map(social =>
                        <a href={social.url} target="_blank">
                            <Image
                                key={social.name}
                                src={"/icons/"+social.name+".png"}
                                alt={social.name}
                                width={32}
                                height={32}
                            />
                        </a>
                    )}
                </div> : null}
            </div>
        </div>

        })}
    </div>


}